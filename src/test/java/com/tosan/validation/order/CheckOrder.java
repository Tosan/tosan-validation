package com.tosan.validation.order;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

/**
 * @author Habib Motallebpour
 * @since 31/10/2016
 */
@GroupSequence({Default.class, First.class, Second.class, Third.class})
public interface CheckOrder {
}
