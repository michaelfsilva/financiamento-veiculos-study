package com.financiamentos.veiculosstudy.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IsoLocalDateSerializer extends StdSerializer<LocalDate> {
    public IsoLocalDateSerializer() {
        this(null);
    }

    public IsoLocalDateSerializer(final Class t) {
        super(t);
    }

    @Override
    public void serialize(final LocalDate value, final JsonGenerator gen, final SerializerProvider arg2)
            throws IOException {
        gen.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(value));
    }
}

