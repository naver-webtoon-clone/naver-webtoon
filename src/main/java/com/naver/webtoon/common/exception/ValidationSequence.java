package com.naver.webtoon.common.exception;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroups.MinimumGroup.class})
public interface ValidationSequence {
}
