package com.locus.project.handlers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locus.project.config.ConfigUtil;
import com.locus.project.config.ServiceConfig;
import com.locus.project.exceptions.BusinessException;
import com.locus.project.handlers.ApiHandler;
import com.locus.project.models.ApiResponse;
import com.locus.project.models.DirectionApiRequest;
import com.locus.project.models.DirectionApiResponse;
import com.locus.project.models.PolylineData;
import com.locus.project.utils.CommonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.locus.project.utils.CommonUtils.*;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */
public class ApiHandlerImpl implements ApiHandler {
    private static final ApiHandlerImpl INSTANCE = new ApiHandlerImpl();
    public static ApiHandlerImpl getInstance() {
        return INSTANCE;
    }

    private ServiceConfig serviceConfig;

    private CloseableHttpAsyncClient httpclient;

    private ApiHandlerImpl() {
        RequestConfig requestConfig;
        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT_IN_SECONDS * 1000)
                .setConnectTimeout(CONNECTION_TIMEOUT_IN_SECONDS * 1000)
                .setSocketTimeout(CONNECTION_TIMEOUT_IN_SECONDS * 1000).build();
        httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).setMaxConnPerRoute(100).setMaxConnTotal(100).build();
        httpclient.start();
        serviceConfig = ConfigUtil.getServiceConfig();

    }

    public DirectionApiResponse getDirections(DirectionApiRequest directionApiRequest) throws IOException, URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        /**
         * SET GOOGLE KEY ON DIRECTION_API REQUEST
         * */

        ObjectMapper objectMapper = new ObjectMapper();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost(serviceConfig.getGoogleApi().getHostname());
        uriBuilder.setScheme(SCHEME);
        uriBuilder.setPath(serviceConfig.getGoogleApi().getDirectionPath());
        uriBuilder.addParameter(ORIGIN, directionApiRequest.getOrigin());
        uriBuilder.addParameter(DESTINATION, directionApiRequest.getDestination());
        uriBuilder.addParameter(CommonUtils.key, directionApiRequest.getKey());

        HttpGet request = new HttpGet(String.valueOf(uriBuilder));

        Future<HttpResponse> future = httpclient.execute(request, null);

        HttpResponse response = future.get(REQUEST_TIME_OUT, TimeUnit.MILLISECONDS);
        HttpEntity he = response.getEntity();

        InputStream responseContent = he.getContent();
        DirectionApiResponse directionApiResponse = new DirectionApiResponse();
        try {
            if (responseContent != null) {
                directionApiResponse = objectMapper.readValue(he.getContent(), DirectionApiResponse.class);

                System.out.println(directionApiResponse);

                return directionApiResponse;
            }
        } catch (IOException io) {
            throw new BusinessException(io.getMessage(), Response.Status.REQUEST_TIMEOUT);
        } finally {
            try{
                if (responseContent != null) {
                    responseContent.close();
                }
            }catch (Exception ignore){}
        }
        System.out.println(directionApiResponse);

        return directionApiResponse;
    }

    @Override
    public ApiResponse pushPolylineData(PolylineData polylineData) throws BusinessException, InterruptedException, ExecutionException, TimeoutException, IOException {
        StringBuilder directionPath = new StringBuilder();
        directionPath.append(serviceConfig.getInternal().getClientPath());
        directionPath.append(serviceConfig.getInternal().getClientId());
        directionPath.append(serviceConfig.getInternal().getUserPath());
        directionPath.append(serviceConfig.getInternal().getUserId());
        directionPath.append(serviceConfig.getInternal().getLocationPath());

        ObjectMapper objectMapper = new ObjectMapper();

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost(serviceConfig.getInternal().getHostname());
        uriBuilder.setScheme(SCHEME);
        uriBuilder.setPath(String.valueOf(directionPath));

        HttpPost request = new HttpPost(String.valueOf(uriBuilder));

        request.setHeader("Authorization", getAuthorizationHeader());
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(polylineData));

        request.setEntity(entity);

        Future<HttpResponse> future = httpclient.execute(request, null);

        HttpResponse response = future.get(REQUEST_TIME_OUT, TimeUnit.MILLISECONDS);
        HttpEntity he = response.getEntity();

        InputStream responseContent = he.getContent();
        ApiResponse apiResponse = new ApiResponse();

        if (responseContent != null) {
            System.out.println("Response: " + response.getStatusLine());

//          apiResponse = objectMapper.readValue(he.getContent(), ApiResponse.class);
        }

        System.out.println(apiResponse);

        return apiResponse;
    }

    private String getAuthorizationHeader() {
        String clientId = serviceConfig.getInternal().getClientId();
        String password = serviceConfig.getInternal().getPassword();
        String authorizationHeader = "Basic " + new String(Base64.encodeBase64((clientId + ":" + password).getBytes()));
        return authorizationHeader;
    }

}
