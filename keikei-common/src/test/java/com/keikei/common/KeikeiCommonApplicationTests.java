package com.keikei.common;

import com.keikei.common.constants.HttpStatus;
import com.keikei.common.constants.UserStatus;
import com.keikei.common.core.exception.user.UserStatusException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
class KeikeiCommonApplicationTests {

    @Test
    void contextLoads() {
       throw new UserStatusException(UserStatus.stop.getInfo());
    }

}
