package com.anshul.tweetbuzz;

import java.io.IOException;

import synesketch.emotion.*;

public class TweetWrapper {

	@SuppressWarnings("unused")
	private double generalWeight = 0;

	@SuppressWarnings("unused")
	private double valence = 0;

	@SuppressWarnings("unused")
	private String strongestEmotion;

	@SuppressWarnings("unused")
	private double strongestEmotionWeight;

	@SuppressWarnings("unused")
	private String text;

	@SuppressWarnings("unused")
	private double latitude;

	@SuppressWarnings("unused")
	private double longitude;

	@SuppressWarnings("unused")
	private String user;

	private static final String ANGER = "anger";
	private static final String DISGUST = "disgust";
	private static final String FEAR = "fear";
	private static final String HAPPINESS = "happiness";
	private static final String NEUTRAL = "neutral";
	private static final String SADNESS = "sadness";
	private static final String SURPRISE = "surprise";

	public static TweetWrapper feel(String text) throws IOException {
		Empathyscope empathyScope = Empathyscope.getInstance();
		EmotionalState state = empathyScope.feel(text);

		return new TweetWrapper(state);
	}

	private TweetWrapper(EmotionalState state) {
		this.text = state.getText();
		this.generalWeight = state.getGeneralWeight();
		this.valence = state.getValence();
		Emotion strongestEmotion = state.getStrongestEmotion();
		if (strongestEmotion.getType() == Emotion.ANGER) {
			this.strongestEmotion = ANGER;
			this.strongestEmotionWeight = state.getAngerWeight();
		} else if (strongestEmotion.getType() == Emotion.DISGUST) {
			this.strongestEmotion = DISGUST;
			this.strongestEmotionWeight = state.getDisgustWeight();
		} else if (strongestEmotion.getType() == Emotion.FEAR) {
			this.strongestEmotion = FEAR;
			this.strongestEmotionWeight = state.getFearWeight();
		} else if (strongestEmotion.getType() == Emotion.HAPPINESS) {
			this.strongestEmotion = HAPPINESS;
			this.strongestEmotionWeight = state.getHappinessWeight();
		} else if (strongestEmotion.getType() == Emotion.NEUTRAL) {
			this.strongestEmotion = NEUTRAL;
		} else if (strongestEmotion.getType() == Emotion.SADNESS) {
			this.strongestEmotion = SADNESS;
			this.strongestEmotionWeight = state.getSadnessWeight();
		} else if (strongestEmotion.getType() == Emotion.SURPRISE) {
			this.strongestEmotion = SURPRISE;
			this.strongestEmotionWeight = state.getSurpriseWeight();
		}
	}

	public void setGeolocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
