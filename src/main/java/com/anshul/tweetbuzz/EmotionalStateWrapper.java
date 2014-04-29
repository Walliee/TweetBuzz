package com.anshul.tweetbuzz;

import java.io.IOException;

import synesketch.emotion.*;

public class EmotionalStateWrapper {
	
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
	
	public static EmotionalStateWrapper feel(String text) throws IOException {
		Empathyscope empathyScope = Empathyscope.getInstance();
		EmotionalState state = empathyScope.feel(text);
		
		return new EmotionalStateWrapper(state);
	}
	
	private EmotionalStateWrapper(EmotionalState state) {
		this.text = state.getText();
		this.generalWeight = state.getGeneralWeight();
		this.valence = state.getValence();
		Emotion strongestEmotion = state.getStrongestEmotion();
		if (strongestEmotion.getType() == Emotion.ANGER) {
			this.strongestEmotion = "anger";
			this.strongestEmotionWeight = state.getAngerWeight();
		}
		else if (strongestEmotion.getType() == Emotion.DISGUST) {
			this.strongestEmotion = "disgust";
			this.strongestEmotionWeight = state.getDisgustWeight();
		}
		else if (strongestEmotion.getType() == Emotion.FEAR) {
			this.strongestEmotion = "fear";
			this.strongestEmotionWeight = state.getFearWeight();
		}
		else if (strongestEmotion.getType() == Emotion.HAPPINESS) {
			this.strongestEmotion = "happiness";
			this.strongestEmotionWeight = state.getHappinessWeight();
		}
		else if (strongestEmotion.getType() == Emotion.NEUTRAL) {
			this.strongestEmotion = "neutral";
		}
		else if (strongestEmotion.getType() == Emotion.SADNESS) {
			this.strongestEmotion = "sadness";
			this.strongestEmotionWeight = state.getSadnessWeight();
		}
		else if (strongestEmotion.getType() == Emotion.SURPRISE) {
			this.strongestEmotion = "surprise";
			this.strongestEmotionWeight = state.getSurpriseWeight();
		}
	}
}
