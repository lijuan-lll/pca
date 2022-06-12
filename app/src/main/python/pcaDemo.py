import numpy as np
import matplotlib.pyplot as plt
import cv2 as cv
from PIL import Image
from os.path import dirname, join
import base64




def color2gray_cv2(color_img_dir, gray_img_dir):
    # 读取彩色图像
    color_img = cv.imread(color_img_dir)
    # cvtColor的第一个参数是处理的图像，第二个是RGB2GRAY
    gray_img = cv.cvtColor(color_img, cv.COLOR_RGB2GRAY)
    # gray_img此时还是二维矩阵表示,所以要实现array到image的转换
    gray = Image.fromarray(gray_img)
    # 将图片保存到当前路径下，参数为保存的文件名
    gray.save(gray_img_dir)
    return gray_img_dir

def PCA(source_image):
    """
    :param source_image:传入灰度图像
    :return:特征提取器
    1.给X矩阵，求X的协方差矩阵。（中心化是求协方差矩阵的一步，中心化后再XX^T形式才和协方差矩阵一样）
    2.对X的协方差矩阵求特征向量。把特征向量按列排布好就是P^T（也就是P的每一行是X的协方差矩阵的一个特征向量）。把特征值排好就是λ（大写）。
    3.降序挑选特征值大的，保留对应的P里的特征向量赋值到P。删掉之后是删掉P^T的若干列，那么也就是删掉P的若干行。所以P是矮矩阵。
    4.Y=PX。这样就把原来特征空间中的所有点获得新的特征组合的表达了。P是矮矩阵，所以实现了降维。
    """
    # 计算协方差矩阵，去中心化
    source_image_norm = source_image - source_image.mean(axis = 0)
    source_image_norm = source_image_norm / source_image.std()
    cov_mat = np.dot(source_image_norm.T,source_image_norm)
    # 计算协方差矩阵的特征值，特征向量
    eigenvalues, eigenvectors = np.linalg.eig(cov_mat)
    # 对特征值进行降序
    sort_index = np.argsort(np.sqrt(eigenvalues), axis=0)[::-1]
    eigenvectors_sort = eigenvectors[:,sort_index]
    eigenvectors_sort = np.real(eigenvectors_sort)

    # 应该得到的是一个提取器，根据这个提取器去得到降维后的图像
    return source_image_norm,eigenvectors_sort

def main(K):
    colorImage_filename = join(dirname(__file__), "tx16.jpg")
    grayImage_filename = join(dirname(__file__), "gray1.jpg")
    resultImage_filename = join(dirname(__file__), "result_picture.jpg")
    # 将彩色图像转为灰色图像
    gray_img_dir = color2gray_cv2(colorImage_filename, grayImage_filename)
    source_image = plt.imread(gray_img_dir)
    # 调用PCA方法获得特征提取器
    source_image_norm,eigenvectors_sort = PCA(source_image)

    # 得到新的图像
    new_image = np.dot(np.dot(source_image_norm, eigenvectors_sort[:, :K]), eigenvectors_sort[:, :K].T) * source_image.std() + source_image.mean(axis = 0)
    image_arr = np.array(new_image)
    cv.imwrite(resultImage_filename, image_arr)
    return resultImage_filename







def test(a,b):
    return a+b