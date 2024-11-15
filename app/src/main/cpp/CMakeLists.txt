# 设置最低 CMake 版本
cmake_minimum_required(VERSION 3.22.1)

# 设置项目名称和 C++ 标准
project(player)
set(CMAKE_CXX_STANDARD 11)

# 打印源代码路径
message(STATUS "Source directory: ${CMAKE_SOURCE_DIR}")

# 打印构建目录路径
message(STATUS "Build directory: ${CMAKE_BINARY_DIR}")

# 打印当前CMakeLists.txt文件所在路径
message(STATUS "Current CMakeLists.txt directory: ${CMAKE_CURRENT_LIST_DIR}")

# 定义路径
#set(PREFIX32 "../buildscripts/prefix/armv7l")
#set(PREFIX64 "../buildscripts/prefix/arm64")
#set(PREFIX_X64 "../buildscripts/prefix/x86_64")
#set(PREFIX_X86 "../buildscripts/prefix/x86")

# 设置架构特定的前缀路径
if (${ANDROID_ABI} STREQUAL "armeabi-v7a")
    set(PREFIX ${PREFIX32})
elseif (${ANDROID_ABI} STREQUAL "arm64-v8a")
    set(PREFIX ${PREFIX64})
elseif (${ANDROID_ABI} STREQUAL "x86_64")
    set(PREFIX ${PREFIX_X64})
elseif (${ANDROID_ABI} STREQUAL "x86")
    set(PREFIX ${PREFIX_X86})
endif ()
unset(LIB_PATH)

# 添加库
include_directories(${PREFIX}/include)
link_directories(${PREFIX}/lib)
# 使用 `find_library` 指定每个库的位置
#    add_library(swresample SHARED IMPORTED)
find_library(LIB_PATH NAMES swresamle)
if (LIB_PATH)
    add_library(swresample SHARED IMPORTED)
    set_target_properties(
            swresample PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#    add_library(postproc SHARED IMPORTED)
find_library(LIB_PATH NAMES postproc)
if (LIB_PATH)
    add_library(postproc SHARED IMPORTED)
    set_target_properties(postproc PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#add_library(avutil SHARED IMPORTED)
find_library(LIB_PATH NAMES avutil)
if (LIB_PATH)
    add_library(avutil SHARED IMPORTED)
    set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#    add_library(avcodec SHARED IMPORTED)
find_library(LIB_PATH NAMES avcodec)
if (LIB_PATH)
    set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#    add_library(avformat SHARED IMPORTED)
find_library(LIB_PATH NAMES avformat)
if (LIB_PATH)
    add_library(avformat SHARED IMPORTED)
    set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#add_library(swscale SHARED IMPORTED)
find_library(LIB_PATH NAMES swscale)
if (LIB_PATH)
    add_library(swscale SHARED IMPORTED)
    set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#add_library(avfilter SHARED IMPORTED)
find_library(LIB_PATH NAMES avfilter)
if (LIB_PATH)
    add_library(avfilter SHARED IMPORTED)
    set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#add_library(avdevice SHARED IMPORTED)
find_library(LIB_PATH NAMES avdevice)
if (LIB_PATH)
    add_library(avdevice SHARED IMPORTED)
    set_target_properties(avdevice PROPERTIES IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI})
endif ()
unset(LIB_PATH)

#add_library(mpv SHARED IMPORTED)
find_library(LIB_PATH NAMES mpv)
if (LIB_PATH)
    add_library(mpv SHARED IMPORTED)
    set_target_properties(mpv PROPERTIES
            IMPORTED_LOCATION ${LIB_PATH}
            LIBRARY_OUTPUT_DIRECTORY
            ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI}
    )
endif ()
unset(LIB_PATH)

# 添加项目的源文件
add_library(${CMAKE_PROJECT_NAME} SHARED
        main.cpp
        render.cpp
        log.cpp
        jni_utils.cpp
        property.cpp
        event.cpp
        thumbnail.cpp
)

# 设置编译选项
target_compile_options(${CMAKE_PROJECT_NAME} PRIVATE -Werror)
set_target_properties(
        ${CMAKE_PROJECT_NAME}
        PROPERTIES
        LIBRARY_OUTPUT_DIRECTORY
        ${CMAKE_SOURCE_DIR}/../libs/${ANDROID_ABI}
)
# 链接库
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        GLESv3
        EGL
        atomic
        swresample
        postproc
        avutil
        avcodec
        avformat
        swscale
        avfilter
        avdevice
        mpv
)

