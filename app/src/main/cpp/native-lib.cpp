#include <jni.h>
#include <string>
using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_hhpost_courier_ui_MainActivity_getJs(
        JNIEnv *env,
        jobject){
    string jiasheng = "衡东的李夹生";
    return env->NewStringUTF(jiasheng.c_str());
}
