package com.songnick.mincy.gpu

/*****
 * @author qfsong
 * Create Time: 2022/7/11
 **/
class GPUImageFilter {

    val NO_FILTER_NAME = "origin"
    val NO_FILTER_VERTEX_SHADER = """attribute vec4 position;
attribute vec4 inputTextureCoordinate;
 
varying vec2 textureCoordinate;
 
void main()
{
    gl_Position = position;
    textureCoordinate = inputTextureCoordinate.xy;
}"""
    val NO_FILTER_FRAGMENT_SHADER = """varying highp vec2 textureCoordinate;
 
uniform sampler2D inputImageTexture;
 
void main()
{
     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);
}"""
}