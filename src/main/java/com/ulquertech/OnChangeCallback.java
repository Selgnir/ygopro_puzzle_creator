package com.ulquertech;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

public interface OnChangeCallback extends Serializable {
    void onChanged(AjaxRequestTarget ajaxRequestTarget);
}
