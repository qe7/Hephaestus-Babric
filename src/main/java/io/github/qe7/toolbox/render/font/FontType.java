package io.github.qe7.toolbox.render.font;

public enum FontType {

    TAHOMA("Tahoma", "Tahoma"),
    SF_UI("SF-UI", "SF-UI-Pro"),
    OPEN_SANS("Open Sans", "OpenSans"),
    ROBOTO("Roboto", "Roboto");

    private final String fontName;
    private final String fontPath;

    FontType(String fontName, String fontPath) {
        this.fontName = fontName;
        this.fontPath = fontPath;
    }

    public String getFontName() {
        return fontName;
    }

    public String getFontPath() {
        return fontPath;
    }
}
