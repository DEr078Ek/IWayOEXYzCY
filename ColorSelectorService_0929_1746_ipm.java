// 代码生成时间: 2025-09-29 17:46:28
package com.example.colorselector;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ColorSelectorService {

    private static final List<String> COLORS = Arrays.asList(
        "Red",
        "Blue",
        "Green",
        "Yellow",
        "Purple",
        "Orange",
        "Black",
        "White"
    );

    /**
     * Retrieves a list of available colors.
     * 
     * @return A list of color names.
     */
    public List<String> getColors() {
        return COLORS;
    }

    /**
     * Selects a color based on the provided index.
     * 
     * @param index The index of the color to select.
     * @return The selected color or an error message if the index is out of range.
     */
    public String selectColor(int index) {
        try {
            if (index < 0 || index >= COLORS.size()) {
                throw new IndexOutOfBoundsException("Index is out of the valid range of color indices.");
            }
            return COLORS.get(index);
        } catch (IndexOutOfBoundsException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Adds a new color to the list of available colors.
     * 
     * @param color The new color to add.
     * @return A success message if the color is added, or an error message if it already exists.
     */
    public String addColor(String color) {
        if (COLORS.contains(color)) {
            return "Error: Color already exists.";
        }
        COLORS.add(color);
        return "Color added successfully.";
    }
}
