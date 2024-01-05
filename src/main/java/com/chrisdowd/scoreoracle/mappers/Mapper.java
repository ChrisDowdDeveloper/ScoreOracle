package com.chrisdowd.scoreoracle.mappers;

/*
 * Used for object mapping in a standard way
*/
public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);

}
