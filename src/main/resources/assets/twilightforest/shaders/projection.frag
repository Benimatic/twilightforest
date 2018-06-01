vec4 roundEither(vec4 valueIn) {
    vec4 valueOut = valueIn + 0.5;

    return valueOut - mod(valueOut, 1.0);
}

vec4 interpolate(vec4 v1, vec4 v2, float placement) {
    return sqrt(((v1 * v1) * (1.0 - placement)) + ((v2 * v2) * placement));
}

void mainImage( in vec2 fragCoord ) {
    // Ticker used for progression.
    float tick = mod(iTime, 2.0) / 2.0;

    // transform screen position into percent
    vec2 uv = fragCoord.xy / iResolution.xy;

    // Sampler
    vec4 texVal = texture( iChannel0, uv );

    float distanceFrom = distance(uv*vec2(2,1), vec2(1,0));

    gl_FragColor = roundEither(texVal - (distanceFrom - (tick * 2.0)));
}