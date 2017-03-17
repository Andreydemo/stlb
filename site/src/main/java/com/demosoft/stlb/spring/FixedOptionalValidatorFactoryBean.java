package com.demosoft.stlb.spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

/**
 * Created by Andrii on 3/17/2017.
 */
@Component
@Primary
public class FixedOptionalValidatorFactoryBean extends OptionalValidatorFactoryBean {
}
