package com.portingdeadmods.nautec.worldgen.injection;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.biome.Climate;

import java.util.ArrayList;
import java.util.List;

public final class ParameterListMerger {
    private static final int DIMENSIONS = 6;

    public static <T> Climate.ParameterList<T> carveAndAppend(Climate.ParameterList<T> base, List<Pair<Climate.ParameterPoint, T>> additions) {
        if (additions.isEmpty()) {
            return base;
        }

        List<Pair<Climate.ParameterPoint, T>> kept = new ArrayList<>(base.values());
        for (Pair<Climate.ParameterPoint, T> addition : additions) {
            List<Pair<Climate.ParameterPoint, T>> carved = new ArrayList<>(kept.size());
            for (Pair<Climate.ParameterPoint, T> existing : kept) {
                for (Climate.ParameterPoint remainder : subtract(existing.getFirst(), addition.getFirst())) {
                    carved.add(Pair.of(remainder, existing.getSecond()));
                }
            }
            kept = carved;
        }

        kept.addAll(additions);
        return new Climate.ParameterList<>(List.copyOf(kept));
    }

    public static List<Climate.ParameterPoint> subtract(Climate.ParameterPoint target, Climate.ParameterPoint cut) {
        for (int dimension = 0; dimension < DIMENSIONS; dimension++) {
            if (!overlaps(get(target, dimension), get(cut, dimension))) {
                return List.of(target);
            }
        }

        List<Climate.ParameterPoint> pieces = new ArrayList<>();
        Climate.ParameterPoint remaining = target;

        for (int dimension = 0; dimension < DIMENSIONS; dimension++) {
            Climate.Parameter range = get(remaining, dimension);
            Climate.Parameter removed = get(cut, dimension);

            if (range.min() < removed.min()) {
                pieces.add(with(remaining, dimension, new Climate.Parameter(range.min(), removed.min() - 1)));
                range = new Climate.Parameter(removed.min(), range.max());
                remaining = with(remaining, dimension, range);
            }

            if (range.max() > removed.max()) {
                pieces.add(with(remaining, dimension, new Climate.Parameter(removed.max() + 1, range.max())));
                remaining = with(remaining, dimension, new Climate.Parameter(range.min(), removed.max()));
            }
        }

        return pieces;
    }

    private static boolean overlaps(Climate.Parameter a, Climate.Parameter b) {
        return a.min() <= b.max() && b.min() <= a.max();
    }

    private static Climate.Parameter get(Climate.ParameterPoint point, int dimension) {
        return switch (dimension) {
            case 0 -> point.temperature();
            case 1 -> point.humidity();
            case 2 -> point.continentalness();
            case 3 -> point.erosion();
            case 4 -> point.depth();
            case 5 -> point.weirdness();
            default -> throw new IndexOutOfBoundsException(dimension);
        };
    }

    private static Climate.ParameterPoint with(Climate.ParameterPoint point, int dimension, Climate.Parameter value) {
        return switch (dimension) {
            case 0 -> new Climate.ParameterPoint(value, point.humidity(), point.continentalness(), point.erosion(), point.depth(), point.weirdness(), point.offset());
            case 1 -> new Climate.ParameterPoint(point.temperature(), value, point.continentalness(), point.erosion(), point.depth(), point.weirdness(), point.offset());
            case 2 -> new Climate.ParameterPoint(point.temperature(), point.humidity(), value, point.erosion(), point.depth(), point.weirdness(), point.offset());
            case 3 -> new Climate.ParameterPoint(point.temperature(), point.humidity(), point.continentalness(), value, point.depth(), point.weirdness(), point.offset());
            case 4 -> new Climate.ParameterPoint(point.temperature(), point.humidity(), point.continentalness(), point.erosion(), value, point.weirdness(), point.offset());
            case 5 -> new Climate.ParameterPoint(point.temperature(), point.humidity(), point.continentalness(), point.erosion(), point.depth(), value, point.offset());
            default -> throw new IndexOutOfBoundsException(dimension);
        };
    }

    private ParameterListMerger() {
    }
}
