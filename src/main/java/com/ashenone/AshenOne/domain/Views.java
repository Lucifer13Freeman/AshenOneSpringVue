package com.ashenone.AshenOne.domain;

public final class Views
{
    public interface Id {}

    public interface IdContent extends Id {}

    public interface FullPost extends IdContent {}

    public interface FullComment extends IdContent {}

    public interface FullProfile extends IdContent {}
}
