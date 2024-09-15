package at.cgimpl.bike_monitor.config;

import at.cgimpl.bike_monitor.dtos.LocationDto;
import at.cgimpl.bike_monitor.entities.Location;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Location, LocationDto>() {
            @Override
            protected void configure() {
                using(ctx -> "https://www.eco-public.com/public2/?id=" + ctx.getSource())
                        .map(source.getId(), destination.getWebsiteUrl());
            }
        });

        return modelMapper;
    }

}
