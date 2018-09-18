package com.haz4j.swagger;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Docket implements DocketApi {

    private String host;

    private String pathMapping;

}
