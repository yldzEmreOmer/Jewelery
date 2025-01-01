package com.star.altineller_kuyumcu.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    GENERAL_EXCEPTION("9999", "Genel bir hata oluştu"),
    USER_NOT_FOUND("1001", "Kullanıcı bulunamadı"),
    USER_ALREADY_EXISTS("1002", "Kullanıcı zaten mevcut"),
    INSUFFICIENT_STOCK("1003", "Yetersiz stok"),
    CATEGORY_NOT_FOUND("2001", "Kategori bulunamadı"),
    CATEGORY_ALREADY_EXISTS("2002", "Kategori zaten mevcut"),
    PRODUCT_NOT_FOUND("3001", "Ürün bulunamadı"),
    PRODUCT_ALREADY_EXISTS("3002", "Ürün zaten mevcut"),
    ORDER_NOT_FOUND("4001", "Sipariş bulunamadı");

    private final String code;
    private final String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
