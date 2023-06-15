package com.keikei.client.service;

import java.util.List;

public interface OnlineService {
    List<String> filterNotOnlineUserId(String useId);
}
