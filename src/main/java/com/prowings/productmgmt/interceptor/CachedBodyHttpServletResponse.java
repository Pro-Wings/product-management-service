package com.prowings.productmgmt.interceptor;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.*;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private ByteArrayOutputStream capture;
    private ServletOutputStream output;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        capture = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (output == null) {
            output = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    capture.write(b);
                    getResponse().getOutputStream().write(b);
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {}
            };
        }
        return output;
    }

    public String getBody() {
        return capture.toString();
    }
}