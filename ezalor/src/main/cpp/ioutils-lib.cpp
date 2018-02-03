//
// Created by huwei on 17-12-20.
//

#include <jni.h>
#include <unistd.h>
#include <stdio.h>

#define F_LEN 1024
extern "C"

JNIEXPORT jstring JNICALL Java_com_wellerv_ezalor_util_Utils_readlink(JNIEnv *env, jclass cls, jint fd) {
    char filename[F_LEN] = "temp";
    char buf[F_LEN];
    snprintf(filename, F_LEN, "/proc/%ld/fd/%d", (long)getpid(), fd);
    readlink(filename, buf, F_LEN);
    return env->NewStringUTF(buf);
}
