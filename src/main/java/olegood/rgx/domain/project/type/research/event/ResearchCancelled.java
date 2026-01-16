package olegood.rgx.domain.project.type.research.event;

import olegood.rgx.domain.project.ProjectEvent;
import olegood.rgx.domain.project.type.research.Research;

public record ResearchCancelled(Research research) implements ProjectEvent {}
