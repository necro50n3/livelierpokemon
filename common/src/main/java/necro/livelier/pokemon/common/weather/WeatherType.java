package necro.livelier.pokemon.common.weather;

public enum WeatherType {
    NONE(0),
    RAIN(0),
    SUN(0),
    SANDSTORM(0),
    SNOW(0),
    HEAVY_RAIN(1),
    HARSH_SUN(1),
    STRONG_WIND(1);

    private final int priority;

    WeatherType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
