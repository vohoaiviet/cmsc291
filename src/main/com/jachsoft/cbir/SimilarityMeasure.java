package com.jachsoft.cbir;

public interface SimilarityMeasure {
        double computeDistance(ImageContentDescriptor input, ImageContentDescriptor target);
}

