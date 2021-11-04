package com.BE.HelpDIANA.helper.converter;

import com.BE.HelpDIANA.helper.constants.SocialLoginType;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
    @Override
    public SocialLoginType convert(String s) {
        return SocialLoginType.valueOf(s.toUpperCase());
    }
}