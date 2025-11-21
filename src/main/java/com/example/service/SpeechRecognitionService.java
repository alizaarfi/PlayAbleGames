package com.example.service;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

//
// Speech recogniser
public class SpeechRecognitionService {
    Configuration configuration = new Configuration();
    configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

    // wrapped by the class
    LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
}
