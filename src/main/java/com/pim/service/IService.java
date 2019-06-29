package com.pim.service;

import java.util.List;

public interface IService<T> {
    List<T> convertIterableToList(Iterable<T> iterable);
}
