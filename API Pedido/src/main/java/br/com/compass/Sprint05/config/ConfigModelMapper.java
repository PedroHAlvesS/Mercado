package br.com.compass.Sprint05.config;

import br.com.compass.Sprint05.util.ConverteDatas;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ConfigModelMapper {

    @Autowired
    private ConverteDatas converteDatas;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Converter String para LocalDateTime
        Converter<String, LocalDateTime> stringParaLocalDateTime = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String string) {
                return converteDatas.formataDataISO8601(string);
            }
        };

        // Converter LocalDateTime para String
        Converter<LocalDateTime, String> localDateParaString = new AbstractConverter<LocalDateTime, String>() {
            @Override
            protected String convert(LocalDateTime localDateTime) {
                return converteDatas.formataDataBrasileira(localDateTime);
            }
        };

        // string blank condition; peguei de > (Model mapper (Git Hub) issue #319)
        Condition<?, ?> isStringBlank = new AbstractCondition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> context) {
                if(context.getSource() instanceof String) {
                    return null!=context.getSource() && !"".equals(context.getSource());
                } else {
                    return context.getSource() != null;
                }
            }
        };


        modelMapper.createTypeMap(String.class, LocalDateTime.class);
        modelMapper.createTypeMap(LocalDateTime.class, String.class);
        modelMapper.addConverter(stringParaLocalDateTime);
        modelMapper.addConverter(localDateParaString);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(isStringBlank);

        return modelMapper;
    }
}
