package com.viryaconsulting.util;

import com.viryaconsulting.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static com.viryaconsulting.util.Constants.FILE_NOT_FOUND;

public class ResourceUtilsTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_success_getFileFromResource_from_resource_directory() throws URISyntaxException, IOException {

        File fileFromResource = ResourceUtils.getFileFromResource("trades.dat");
        File mockInputFile = getMockInputFile();

        Assert.assertNotNull(fileFromResource);
        Assert.assertEquals(getFileContent(mockInputFile), getFileContent(fileFromResource));
    }

    @Test
    public void test_Exception_getFileFromResource_from_resource_directory() throws URISyntaxException, IOException {

        String fileName = "notfound";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(String.format(String.format(FILE_NOT_FOUND, fileName)));

        File fileFromResource = ResourceUtils.getFileFromResource(fileName);
        File mockInputFile = getMockInputFile();

        Assert.assertNotNull(fileFromResource);
        Assert.assertEquals(getFileContent(mockInputFile), getFileContent(fileFromResource));
    }

    @Test
    public void test_success_getFileFromResource_from_Absolute_Path() throws URISyntaxException, IOException {

        File mockInputFile = getMockInputFile();
        File file = temporaryFolder.newFile("trades.dat");
        Files.copy(mockInputFile.toPath(), new FileOutputStream(file));

        File fileFromResource = ResourceUtils.getFileFromResource(file.toURI().getPath());

        Assert.assertNotNull(fileFromResource);
        Assert.assertEquals(getFileContent(mockInputFile), getFileContent(fileFromResource));
    }

    @Test
    public void test_createTemporyFile() throws IOException {

        File temporyFile = ResourceUtils.createTemporyFile("prefix", ".suffix");

        Assert.assertNotNull(temporyFile);
        Assert.assertTrue(temporyFile.exists());
        Assert.assertTrue(temporyFile.getPath().contains("prefix"));
        Assert.assertTrue(temporyFile.getPath().endsWith(".suffix"));
    }

    @Test
    public void test_getLinesFromFile_success() throws IOException {

        File mockInputFile = getMockInputFile();
        String[] mockLines = getMockLines();

        String[] linesFromFile = ResourceUtils.getLinesFromFile(mockInputFile);
        Assert.assertNotNull(linesFromFile);
        Assert.assertArrayEquals(mockLines, linesFromFile);

    }

    @Test
    public void test_getParametersFromLine_success() throws IOException {

        String[] mockLines = getMockLines();
        String[] mockParametersOfLine = getMockParametersOfLine();

        String[] linesFromFile = ResourceUtils.getParametersFromLine(mockLines[0]);
        Assert.assertNotNull(linesFromFile);
        Assert.assertArrayEquals(mockParametersOfLine, mockParametersOfLine);

    }

    @Test
    public void test_Exception_Create_Instance() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        expectedException.expect(InvocationTargetException.class);

        Constructor<ResourceUtils> constructor = ResourceUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ResourceUtils resourceUtils = constructor.newInstance();

    }

    @Test
    public void test_success_createFileFromInputStream() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getMockInputFile());
        File fileFromResource = ResourceUtils.createFileFromInputStream(fileInputStream, "trades.dat");
        File mockInputFile = getMockInputFile();

        Assert.assertNotNull(fileFromResource);
        Assert.assertEquals(getFileContent(mockInputFile), getFileContent(fileFromResource));
    }

    @Before
    public void init() throws IOException {
        if (resourceUtilsMockedStatic != null) {
            resourceUtilsMockedStatic.close();
            resourceUtilsMockedStatic = null;
        }
    }
}
