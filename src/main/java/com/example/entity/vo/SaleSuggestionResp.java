package com.example.entity.vo;

import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/04/26 09/19
 */
@Data
public class SaleSuggestionResp {

    private String name;

    private List<SaleSuggestion> saleSuggestionList;
}
