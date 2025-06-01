package com.joseph.finance.application.commands;

public record AuthenticatedCommand(String accessToken, String refreshToken) {
}
