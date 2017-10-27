package com.locus.project.models;

import lombok.Data;

import java.io.Serializable;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */

@Data
public class Duration implements Serializable {
    private String text;
    private long value;

}
