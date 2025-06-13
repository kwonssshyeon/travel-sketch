package com.ssafy.trip.util;

public class GeoUtils {
    private static final double EARTH_RADIUS_METERS = 6371000.0;

    /**
     * 중심점 기준으로 거리 범위의 박스 영역 계산
     */
    public static BoxArea calculateBoxRange(double lat, double lon, int distanceMeters) {
        double latDelta = Math.toDegrees(distanceMeters / EARTH_RADIUS_METERS);
        double lonDelta = Math.toDegrees(distanceMeters / (EARTH_RADIUS_METERS * Math.cos(Math.toRadians(lat))));

        return new BoxArea(
                lat - latDelta,
                lat + latDelta,
                lon - lonDelta,
                lon + lonDelta
        );
    }

    public record BoxArea(double minLat, double maxLat, double minLon, double maxLon) {}
}
