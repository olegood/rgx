package olegood.rgx.mapper;

import java.util.List;

public record EnrollmentData(Long id, String type, String status, List<MarkerData> markers) {}
