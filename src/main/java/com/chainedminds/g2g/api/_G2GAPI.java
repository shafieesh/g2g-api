package com.chainedminds.g2g.api;

import com.chainedminds._Codes;
import com.chainedminds.api._API;
import com.chainedminds.g2g.api.models._G2GData;
import com.chainedminds.utilities.SocketPool;
import com.chainedminds.utilities.json.Json;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class _G2GAPI extends _API {

    public static _G2GAPI INSTANCE;
    public static final SocketPool POOL = new SocketPool("engine-g2g.chainedminds.com", 4595, 5);
    public static final ExecutorService ASYNC_POOL_EXECUTOR = Executors.newCachedThreadPool();

    public static synchronized _G2GAPI get() {

        if (INSTANCE == null) {

            INSTANCE = new _G2GAPI();
        }

        return INSTANCE;
    }

    public static void config(int id, String credential, String appName, String language) {

        _G2GData.accountID = id;
        _G2GData.accountCredential = credential;
        _G2GData.clientAppName = appName;
        _G2GData.clientLanguage = language;
    }

    public void call(_G2GData request, boolean async, ApiCallback callback) {

        callPool(request, async, callback);
    }

    public void callHttp(_G2GData request, boolean async, ApiCallback callback) {

        String requestJson = Json.getString(request);

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(requestJson, mediaType);

        Request.Builder builder = new Request.Builder();
        builder.url("https://api-g2g.chainedminds.com/v2/");
        builder.post(body);

        call(builder, async, callback);
    }

    public void callPool(_G2GData request, boolean async, ApiCallback callback) {

        if (async) {

            ASYNC_POOL_EXECUTOR.execute(() -> {

                byte[] requestBytes = Json.getBytes(request);

                byte[] responseBytes = SocketPool.transfer(POOL, requestBytes);

                if (callback != null) {

                    if (responseBytes != null) {

                        String responseString = new String(responseBytes);

                        callback.onResponse(200, responseString);
                        callback.onResponse(200, (Map<String, List<String>>) null, responseString);
                        callback.onResponse(200, (Headers) null, responseString);

                    } else {

                        callback.onError(new RuntimeException("Unknown error"));
                        callback.onError("Unknown error", "Unknown error");
                        callback.onError("Unknown error", new RuntimeException("Unknown error"));
                    }
                }
            });

        } else {

            byte[] requestBytes = Json.getBytes(request);

            byte[] responseBytes = SocketPool.transfer(POOL, requestBytes);

            if (callback != null) {

                if (responseBytes != null) {

                    String responseString = new String(responseBytes);

                    callback.onResponse(200, responseString);
                    callback.onResponse(200, (Map<String, List<String>>) null, responseString);
                    callback.onResponse(200, (Headers) null, responseString);

                } else {

                    callback.onError(new RuntimeException("Unknown error"));
                    callback.onError("Unknown error", "Unknown error");
                    callback.onError("Unknown error", new RuntimeException("Unknown error"));
                }
            }
        }
    }

    public boolean upload(File file) {

        AtomicBoolean wasSuccessful = new AtomicBoolean(false);

        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

        Request.Builder builder = new Request.Builder();
        builder.url("https://upload-g2g.chainedminds.com/" + file.getName());
        builder.post(fileBody);

        _API.instance().call(builder, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI upload " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, Map<String, List<String>> headers, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    wasSuccessful.set(true);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return wasSuccessful.get();
    }

    public _G2GData getOrdersG2G(long lastUpdate) {

        AtomicReference<_G2GData> data = new AtomicReference<>();

        _G2GData requestData = new _G2GData();
        requestData.request = 1009;
        requestData.subRequest = 2022;
        requestData.order = new _G2GData.Order();
        requestData.order.lastUpdate = lastUpdate;

        call(requestData, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI getOrdersG2G " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    data.set(responseData);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return data.get();
    }

    public _G2GData getOrdersEldorado(long lastUpdate) {

        AtomicReference<_G2GData> data = new AtomicReference<>();

        _G2GData requestData = new _G2GData();
        requestData.request = 1009;
        requestData.subRequest = 2035;
        requestData.order = new _G2GData.Order();
        requestData.order.lastUpdate = lastUpdate;

        call(requestData, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI getOrdersEldorado " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    data.set(responseData);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return data.get();
    }

    public boolean changeOfferPriceV1(String link, String price, String store) {

        AtomicBoolean wasSuccessful = new AtomicBoolean();

        _G2GData requestData = new _G2GData();
        requestData.request = 1009;
        requestData.subRequest = 2020;
        requestData.offer = new _G2GData.Offer();
        requestData.offer.link = link;
        requestData.offer.price = price;
        requestData.offer.store = store;

        call(requestData, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI changeOfferPriceV1 " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    wasSuccessful.set(true);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return wasSuccessful.get();
    }

    public boolean changeOfferPriceV2(String id, String price, String store) {

        AtomicBoolean wasSuccessful = new AtomicBoolean();

        _G2GData requestData = new _G2GData();
        requestData.request = 1009;
        requestData.subRequest = 2027;
        requestData.offer = new _G2GData.Offer();
        requestData.offer.id = id;
        requestData.offer.price = price;
        requestData.offer.store = store;

        call(requestData, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI changeOfferPriceV2 " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    wasSuccessful.set(true);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return wasSuccessful.get();
    }

    public _G2GData getCookiesStorages() {

        AtomicReference<_G2GData> data = new AtomicReference<>();

        _G2GData requestData = new _G2GData();
        requestData.request = 1009;
        requestData.subRequest = 2024;

        call(requestData, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI getCookiesStorages " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    data.set(responseData);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return data.get();
    }

    public List<_G2GData.Customer> getCustomers() {

        AtomicReference<List<_G2GData.Customer>> customersHolder = new AtomicReference<>();

        _G2GData requestData = new _G2GData();
        requestData.request = 1009;
        requestData.subRequest = 2029;

        call(requestData, false, new ApiCallback() {
            @Override
            public void onError(String error, String message) {

                System.err.println("G2GAPI getCustomers " + error + " : " + message);
            }

            @Override
            public void onResponse(int code, String response) {

                if (code != 200) {

                    throw new RuntimeException("Response code : " + code);
                }

                _G2GData responseData = Json.getObject(response, _G2GData.class);

                if (responseData != null && responseData.response == _Codes.RESPONSE_OK) {

                    customersHolder.set(responseData.customers);

                } else {

                    throw new RuntimeException("Response  : " + response);
                }
            }
        });

        return customersHolder.get();
    }

    public static boolean validateNumber(String input) {

        try {

            Double.parseDouble(input);

            return true;

        } catch (Exception ignored) {

            return false;
        }
    }
}
