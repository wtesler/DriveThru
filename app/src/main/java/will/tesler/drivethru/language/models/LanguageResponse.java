package will.tesler.drivethru.language.models;

public class LanguageResponse {

    public Sentence[] sentences;
    public Token[] tokens;
    public Entity[] entities;
    public Sentiment documentSentiment;
    public @LanguageDocument.Iso String language;
}
