package will.tesler.drivethru.language.models;

public class GcsLanguageRequest {

    public GcsLanguageDocument document;
    public Features features;
    public @LanguageRequest.EncodingType String encodingType;
}
