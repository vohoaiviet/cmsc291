package com.jachsoft.cobair;

public interface SimilarityMeasure {
	double computeDistance(ImageContentDescriptor input, ImageContentDescriptor target);
}
