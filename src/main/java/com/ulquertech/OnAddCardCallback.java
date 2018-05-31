package com.ulquertech;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

public interface OnAddCardCallback extends Serializable {
    void onAddCard(AjaxRequestTarget ajaxRequestTarget, StringBuilder stringBuilder);
}
