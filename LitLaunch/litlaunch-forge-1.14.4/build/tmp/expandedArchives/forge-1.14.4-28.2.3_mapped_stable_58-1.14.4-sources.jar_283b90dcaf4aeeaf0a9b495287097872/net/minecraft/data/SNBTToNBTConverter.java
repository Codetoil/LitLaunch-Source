package net.minecraft.data;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SNBTToNBTConverter implements IDataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private final DataGenerator generator;
   private final List<SNBTToNBTConverter.ITransformer> field_225370_d = Lists.newArrayList();

   public SNBTToNBTConverter(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public SNBTToNBTConverter func_225369_a(SNBTToNBTConverter.ITransformer p_225369_1_) {
      this.field_225370_d.add(p_225369_1_);
      return this;
   }

   private CompoundNBT func_225368_a(String p_225368_1_, CompoundNBT p_225368_2_) {
      CompoundNBT compoundnbt = p_225368_2_;

      for(SNBTToNBTConverter.ITransformer snbttonbtconverter$itransformer : this.field_225370_d) {
         compoundnbt = snbttonbtconverter$itransformer.func_225371_a(p_225368_1_, compoundnbt);
      }

      return compoundnbt;
   }

   public void act(DirectoryCache cache) throws IOException {
      Path path = this.generator.getOutputFolder();

      for(Path path1 : this.generator.getInputFolders()) {
         Files.walk(path1).filter((p_200422_0_) -> {
            return p_200422_0_.toString().endsWith(".snbt");
         }).forEach((p_200421_4_) -> {
            this.convert(cache, p_200421_4_, this.getFileName(path1, p_200421_4_), path);
         });
      }

   }

   public String getName() {
      return "SNBT -> NBT";
   }

   private String getFileName(Path inputFolder, Path fileIn) {
      String s = inputFolder.relativize(fileIn).toString().replaceAll("\\\\", "/");
      return s.substring(0, s.length() - ".snbt".length());
   }

   private void convert(DirectoryCache cache, Path fileIn, String name, Path outputFolder) {
      try {
         Path path = outputFolder.resolve(name + ".nbt");

         try (BufferedReader bufferedreader = Files.newBufferedReader(fileIn)) {
            String s = IOUtils.toString((Reader)bufferedreader);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            CompressedStreamTools.writeCompressed(this.func_225368_a(name, JsonToNBT.getTagFromJson(s)), bytearrayoutputstream);
            String s1 = HASH_FUNCTION.hashBytes(bytearrayoutputstream.toByteArray()).toString();
            if (!Objects.equals(cache.getPreviousHash(path), s1) || !Files.exists(path)) {
               Files.createDirectories(path.getParent());

               try (OutputStream outputstream = Files.newOutputStream(path)) {
                  outputstream.write(bytearrayoutputstream.toByteArray());
               }
            }

            cache.recordHash(path, s1);
         }
      } catch (CommandSyntaxException commandsyntaxexception) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", name, fileIn, commandsyntaxexception);
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {}", name, fileIn, ioexception);
      }

   }

   @FunctionalInterface
   public interface ITransformer {
      CompoundNBT func_225371_a(String p_225371_1_, CompoundNBT p_225371_2_);
   }
}