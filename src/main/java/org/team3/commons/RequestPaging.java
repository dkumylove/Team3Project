package org.team3.commons;

import lombok.Data;

@Data
public class RequestPaging {
    private int page = 1;
    private int limit = 20;
}
