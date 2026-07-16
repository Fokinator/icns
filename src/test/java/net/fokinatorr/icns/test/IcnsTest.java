package net.fokinatorr.icns.test;

import it.unimi.dsi.io.ByteBufferInputStream;
import lombok.NonNull;
import net.fokinatorr.icns.IcnsIcons;
import net.fokinatorr.icns.io.IcnsDecoder;
import net.fokinatorr.icns.io.IcnsEncoder;
import net.fokinatorr.icns.records.IcnsIcon;
import net.fokinatorr.icns.type.IcnsIconType;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static net.fokinatorr.icns.type.IcnsIconType.*;
import static org.junit.jupiter.api.Assertions.*;

public class IcnsTest {

    @Test
    void testEncoder() throws IOException {
        // @formatter:off
        IcnsIcons icons = IcnsIcons.of(
                loadIcon(ICNS_16x16_24BIT_IMAGE,         "/is32"),
                loadIcon(ICNS_16x16_8BIT_MASK,           "/s8mk"),
                loadIcon(ICNS_32x32_24BIT_IMAGE,         "/il32"),
                loadIcon(ICNS_32x32_8BIT_MASK,           "/l8mk"),
                loadIcon(ICNS_128x128_JPEG_PNG_IMAGE,    "/ic07_128x128.png"),
                loadIcon(ICNS_256x256_JPEG_PNG_IMAGE,    "/ic08_256x256.png"),
                loadIcon(ICNS_512x512_JPEG_PNG_IMAGE,    "/ic09_512x512.png"),
                loadIcon(ICNS_16x16_2X_JPEG_PNG_IMAGE,   "/ic11_16x16@2x.png"),
                loadIcon(ICNS_32x32_2X_JPEG_PNG_IMAGE,   "/ic12_32x32@2x.png"),
                loadIcon(ICNS_128x128_2X_JPEG_PNG_IMAGE, "/ic13_128x128@2x.png"),
                loadIcon(ICNS_256x256_2X_JPEG_PNG_IMAGE, "/ic14_256x256@2x.png"),
                loadIcon(ICNS_512x512_2X_JPEG_PNG_IMAGE, "/ic10_512x512@2x.png")
        );
        // @formatter:on

        assertEquals(12, icons.records().size());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        IcnsEncoder.write(icons, os);

        assertArrayEquals(Files.readAllBytes(getResource("/compass.icns")), os.toByteArray());
    }

    @Test
    void testDecoder() throws IOException {
        IcnsIcons icons;
        try (var in = IcnsTest.class.getResourceAsStream("/compass.icns")) {
            icons = IcnsDecoder.read(in);
        }
        assertEquals(12, icons.records().size());

        int imagesLoaded = 0;
        for (var rec : icons.records()) {
            if (rec instanceof IcnsIcon icon && parseImage(icon)) {
                imagesLoaded++;
            }
        }

        assertEquals(8, imagesLoaded);
    }

    private @NonNull IcnsIcon loadIcon(IcnsIconType type, String res) throws IOException {
        ByteBuffer buf;
        try (var in = IcnsTest.class.getResourceAsStream(res)) {
            assertNotNull(in);
            buf = ByteBuffer.wrap(in.readAllBytes());
        }
        return new IcnsIcon(type, buf);
    }

    private static boolean parseImage(IcnsIcon icon) throws IOException {
        if (icon.type().name().contains("PNG")) {
            BufferedImage image = ImageIO.read(new ByteBufferInputStream(icon.data()));
            assertNotNull(image);
            assertEquals(icon.type().getWidth(), image.getWidth());
            assertEquals(icon.type().getHeight(), image.getHeight());

            return true;
        }
        return false;
    }

    private static Path getResource(String name) {
        try {
            return Path.of(Objects.requireNonNull(IcnsTest.class.getResource(name)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
