LOCAL_PATH		:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := ImageBlurJni
LOCAL_SRC_FILES := stackblur.c com_lauzy_freedom_librarys_view_blur_ImageBlur.c load.c
LOCAL_LDLIBS    := -lm -llog -ljnigraphics

include $(BUILD_SHARED_LIBRARY)