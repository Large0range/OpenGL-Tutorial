package jupiter;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Model {
	
	private float[] vertexArray;
	private int[] indices;
	
	private int vboID, iboID;
	
	public Model(Vector3f position, Vector2f scale, Vector3f color) {
		vertexArray = new float[] {
				position.x - scale.x / 2, position.y - scale.y / 2, position.z,  color.x, color.y, color.z, 1.0f,
				position.x - scale.x / 2, position.y + scale.y / 2, position.z,  color.x, color.y, color.z, 1.0f,
				position.x + scale.x / 2, position.y + scale.y / 2, position.z,  color.x, color.y, color.z, 1.0f
		};
		
		indices = new int[] {
				0, 1, 2
		};
	}
	
	public void create() {
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		iboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
		indexBuffer.put(indices).flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void delete() {
		glDeleteBuffers(vboID);
	}
	
	public void setPointers() {
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * Float.BYTES, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES);
	}
	
	public int getVboID() {
		return vboID;
	}

	public int getIboID() {
		return iboID;
	}
	
	public int getVertexCount() {
		return vertexArray.length / 3;
	}
	
	public int getIndexCount() {
		return indices.length;
	}

}
