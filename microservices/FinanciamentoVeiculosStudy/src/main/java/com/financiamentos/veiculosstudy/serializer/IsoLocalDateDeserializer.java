package com.financiamentos.veiculosstudy.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IsoLocalDateDeserializer extends StdDeserializer<LocalDate> {
    public IsoLocalDateDeserializer() {
        this(null);
    }

    public IsoLocalDateDeserializer(final Class t) {
        super(t);
    }

    @Override
    public LocalDate deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException {
        final JsonNode node = p.getCodec().readTree(p);
        return LocalDate.parse(node.textValue(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
