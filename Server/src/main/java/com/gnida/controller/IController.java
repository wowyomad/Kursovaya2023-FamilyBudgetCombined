package com.gnida.controller;

import com.gnida.model.Request;
import com.gnida.model.Response;

public interface IController {
    Response handleGet(Request request);
    Response handlePost(Request request);
    Response handleDelete(Request request);
    Response handleUpdate(Request request);
}
