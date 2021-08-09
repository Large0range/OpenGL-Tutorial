package jupiter;

import static org.lwjgl.opengl.GL40.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Shader {
	
	private String vertexFile = "/vertex.glsl";
	private String fragmentFile = "/fragment.glsl";
	
	private int programID, vertexID, fragmentID;
	
	public Shader() {}
	
	public Shader(String vertexFile, String fragmentFile) {
		this.vertexFile = vertexFile;
		this.fragmentFile = fragmentFile;
	}
	
	private String readFile(String fileName) {
		String string = "";
		File file = new File(this.getClass().getResource(fileName).getFile());
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (scan.hasNextLine()) {
			string += scan.nextLine() + "\n";
		}
		
		return string;
	}
	
	private int loadShader(int type, String file) {
		int id = glCreateShader(type);
		glShaderSource(id, readFile(file));
		glCompileShader(id);
		
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println("Could Not Compile " + file);
			System.out.println(glGetShaderInfoLog(id));
		}
		
		return id;
	}
	
	public void create() {
		programID = glCreateProgram();
		
		vertexID = loadShader(GL_VERTEX_SHADER, vertexFile);
		fragmentID = loadShader(GL_FRAGMENT_SHADER, fragmentFile);
		
		glAttachShader(programID, vertexID);
		glAttachShader(programID, fragmentID);
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	public void use() {
		glUseProgram(programID);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public void delete() {
		stop();
		glDetachShader(programID, vertexID);
		glDetachShader(programID, fragmentID);
		glDeleteShader(vertexID);
		glDeleteShader(fragmentID);
		glDeleteProgram(programID);
	}

}
