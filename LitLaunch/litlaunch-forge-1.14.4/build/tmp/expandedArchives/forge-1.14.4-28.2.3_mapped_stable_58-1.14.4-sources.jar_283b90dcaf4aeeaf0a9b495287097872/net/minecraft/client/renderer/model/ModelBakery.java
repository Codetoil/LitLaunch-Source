package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.multipart.Multipart;
import net.minecraft.client.renderer.model.multipart.Selector;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModelBakery {
   public static final ResourceLocation LOCATION_FIRE_0 = new ResourceLocation("block/fire_0");
   public static final ResourceLocation LOCATION_FIRE_1 = new ResourceLocation("block/fire_1");
   public static final ResourceLocation LOCATION_LAVA_FLOW = new ResourceLocation("block/lava_flow");
   public static final ResourceLocation LOCATION_WATER_FLOW = new ResourceLocation("block/water_flow");
   public static final ResourceLocation LOCATION_WATER_OVERLAY = new ResourceLocation("block/water_overlay");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_0 = new ResourceLocation("block/destroy_stage_0");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_1 = new ResourceLocation("block/destroy_stage_1");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_2 = new ResourceLocation("block/destroy_stage_2");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_3 = new ResourceLocation("block/destroy_stage_3");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_4 = new ResourceLocation("block/destroy_stage_4");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_5 = new ResourceLocation("block/destroy_stage_5");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_6 = new ResourceLocation("block/destroy_stage_6");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_7 = new ResourceLocation("block/destroy_stage_7");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_8 = new ResourceLocation("block/destroy_stage_8");
   public static final ResourceLocation LOCATION_DESTROY_STAGE_9 = new ResourceLocation("block/destroy_stage_9");
   protected static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet(LOCATION_WATER_FLOW, LOCATION_LAVA_FLOW, LOCATION_WATER_OVERLAY, LOCATION_FIRE_0, LOCATION_FIRE_1, LOCATION_DESTROY_STAGE_0, LOCATION_DESTROY_STAGE_1, LOCATION_DESTROY_STAGE_2, LOCATION_DESTROY_STAGE_3, LOCATION_DESTROY_STAGE_4, LOCATION_DESTROY_STAGE_5, LOCATION_DESTROY_STAGE_6, LOCATION_DESTROY_STAGE_7, LOCATION_DESTROY_STAGE_8, LOCATION_DESTROY_STAGE_9, new ResourceLocation("item/empty_armor_slot_helmet"), new ResourceLocation("item/empty_armor_slot_chestplate"), new ResourceLocation("item/empty_armor_slot_leggings"), new ResourceLocation("item/empty_armor_slot_boots"), new ResourceLocation("item/empty_armor_slot_shield"));
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
   @VisibleForTesting
   public static final String MISSING_MODEL_MESH = ("{    'textures': {       'particle': '" + MissingTextureSprite.getLocation().getPath() + "',       'missingno': '" + MissingTextureSprite.getLocation().getPath() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}").replace('\'', '"');
   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap(ImmutableMap.of("missing", MISSING_MODEL_MESH));
   private static final Splitter SPLITTER_COMMA = Splitter.on(',');
   private static final Splitter EQUALS_SPLITTER = Splitter.on('=').limit(2);
   public static final BlockModel MODEL_GENERATED = Util.make(BlockModel.deserialize("{}"), (p_209273_0_) -> {
      p_209273_0_.name = "generation marker";
   });
   public static final BlockModel MODEL_ENTITY = Util.make(BlockModel.deserialize("{}"), (p_209274_0_) -> {
      p_209274_0_.name = "block entity marker";
   });
   private static final StateContainer<Block, BlockState> STATE_CONTAINER_ITEM_FRAME = (new StateContainer.Builder<Block, BlockState>(Blocks.AIR)).add(BooleanProperty.create("map")).create(BlockState::new);
   private static final ItemModelGenerator field_217854_z = new ItemModelGenerator();
   private static final Map<ResourceLocation, StateContainer<Block, BlockState>> STATE_CONTAINER_OVERRIDES = ImmutableMap.of(new ResourceLocation("item_frame"), STATE_CONTAINER_ITEM_FRAME);
   protected final IResourceManager resourceManager;
   protected final AtlasTexture textureMap;
   private final BlockColors field_225365_D;
   private final Set<ResourceLocation> field_217848_D = Sets.newHashSet();
   private final BlockModelDefinition.ContainerHolder containerHolder = new BlockModelDefinition.ContainerHolder();
   private final Map<ResourceLocation, IUnbakedModel> field_217849_F = Maps.newHashMap();
   private final Map<Triple<ResourceLocation, net.minecraftforge.common.model.IModelState, Boolean>, IBakedModel> field_217850_G = Maps.newHashMap();
   private final Map<ResourceLocation, IUnbakedModel> field_217851_H = Maps.newHashMap();
   private final Map<ResourceLocation, IBakedModel> field_217852_I = Maps.newHashMap();
   private AtlasTexture.SheetData field_217853_J;
   private int field_225366_L = 1;
   private final Object2IntMap<BlockState> field_225367_M = Util.make(new Object2IntOpenHashMap<>(), (p_225357_0_) -> {
      p_225357_0_.defaultReturnValue(-1);
   });

   public ModelBakery(IResourceManager p_i51735_1_, AtlasTexture p_i51735_2_, BlockColors p_i51735_3_, IProfiler p_i51735_4_) {
      this(p_i51735_1_, p_i51735_2_, p_i51735_3_, true);
      processLoading(p_i51735_4_);
   }

   protected ModelBakery(IResourceManager p_i51735_1_, AtlasTexture p_i51735_2_, BlockColors p_i51735_3_, boolean vanillaBakery) {
      this.resourceManager = p_i51735_1_;
      this.textureMap = p_i51735_2_;
      this.field_225365_D = p_i51735_3_;
   }

   protected void processLoading(IProfiler p_i51735_4_) {
      p_i51735_4_.startSection("missing_model");

      try {
         this.field_217849_F.put(MODEL_MISSING, this.loadModel(MODEL_MISSING));
         this.func_217843_a(MODEL_MISSING);
      } catch (IOException ioexception) {
         LOGGER.error("Error loading missing model, should never happen :(", (Throwable)ioexception);
         throw new RuntimeException(ioexception);
      }

      p_i51735_4_.endStartSection("static_definitions");
      STATE_CONTAINER_OVERRIDES.forEach((p_217842_1_, p_217842_2_) -> {
         p_217842_2_.getValidStates().forEach((p_217836_2_) -> {
            this.func_217843_a(BlockModelShapes.getModelLocation(p_217842_1_, p_217836_2_));
         });
      });
      p_i51735_4_.endStartSection("blocks");

      for(Block block : Registry.BLOCK) {
         block.getStateContainer().getValidStates().forEach((p_217837_1_) -> {
            this.func_217843_a(BlockModelShapes.getModelLocation(p_217837_1_));
         });
      }

      p_i51735_4_.endStartSection("items");

      for(ResourceLocation resourcelocation : Registry.ITEM.keySet()) {
         this.func_217843_a(new ModelResourceLocation(resourcelocation, "inventory"));
      }

      p_i51735_4_.endStartSection("special");
      this.func_217843_a(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      for (ResourceLocation rl : getSpecialModels()) {
          // Same as func_217843_a but without restricting to MRL's
          IUnbakedModel iunbakedmodel = this.getUnbakedModel(rl);
          this.field_217849_F.put(rl, iunbakedmodel);
          this.field_217851_H.put(rl, iunbakedmodel);
      }
      p_i51735_4_.endStartSection("textures");
      Set<String> set = Sets.newLinkedHashSet();
      Set<ResourceLocation> set1 = this.field_217851_H.values().stream().flatMap((p_217838_2_) -> {
         return p_217838_2_.getTextures(this::getUnbakedModel, set).stream();
      }).collect(Collectors.toSet());
      set1.addAll(LOCATIONS_BUILTIN_TEXTURES);
      net.minecraftforge.client.ForgeHooksClient.gatherFluidTextures(set1);
      set.forEach((p_217833_0_) -> {
         LOGGER.warn("Unable to resolve texture reference: {}", (Object)p_217833_0_);
      });
      p_i51735_4_.endStartSection("stitching");
      this.field_217853_J = this.textureMap.stitch(this.resourceManager, set1, p_i51735_4_);
      p_i51735_4_.endSection();
   }

   public void func_217844_a(IProfiler p_217844_1_) {
      p_217844_1_.startSection("atlas");
      this.textureMap.upload(this.field_217853_J);
      p_217844_1_.endStartSection("baking");
      this.field_217851_H.keySet().forEach((p_217835_1_) -> {
         IBakedModel ibakedmodel = null;

         try {
            ibakedmodel = this.func_217845_a(p_217835_1_, ModelRotation.X0_Y0);
         } catch (Exception exception) {
            LOGGER.warn("Unable to bake model: '{}': {}", p_217835_1_, exception);
         }

         if (ibakedmodel != null) {
            this.field_217852_I.put(p_217835_1_, ibakedmodel);
         }

      });
      p_217844_1_.endSection();
   }

   private static Predicate<BlockState> parseVariantKey(StateContainer<Block, BlockState> containerIn, String variantIn) {
      Map<IProperty<?>, Comparable<?>> map = Maps.newHashMap();

      for(String s : SPLITTER_COMMA.split(variantIn)) {
         Iterator<String> iterator = EQUALS_SPLITTER.split(s).iterator();
         if (iterator.hasNext()) {
            String s1 = iterator.next();
            IProperty<?> iproperty = containerIn.getProperty(s1);
            if (iproperty != null && iterator.hasNext()) {
               String s2 = iterator.next();
               Comparable<?> comparable = parseValue(iproperty, s2);
               if (comparable == null) {
                  throw new RuntimeException("Unknown value: '" + s2 + "' for blockstate property: '" + s1 + "' " + iproperty.getAllowedValues());
               }

               map.put(iproperty, comparable);
            } else if (!s1.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + s1 + "'");
            }
         }
      }

      Block block = containerIn.getOwner();
      return (p_217840_2_) -> {
         if (p_217840_2_ != null && block == p_217840_2_.getBlock()) {
            for(Entry<IProperty<?>, Comparable<?>> entry : map.entrySet()) {
               if (!Objects.equals(p_217840_2_.get(entry.getKey()), entry.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T parseValue(IProperty<T> property, String value) {
      return (T)(property.parseValue(value).orElse((T)null));
   }

   public IUnbakedModel getUnbakedModel(ResourceLocation modelLocation) {
      if (this.field_217849_F.containsKey(modelLocation)) {
         return this.field_217849_F.get(modelLocation);
      } else if (this.field_217848_D.contains(modelLocation)) {
         throw new IllegalStateException("Circular reference while loading " + modelLocation);
      } else {
         this.field_217848_D.add(modelLocation);
         IUnbakedModel iunbakedmodel = this.field_217849_F.get(MODEL_MISSING);

         while(!this.field_217848_D.isEmpty()) {
            ResourceLocation resourcelocation = this.field_217848_D.iterator().next();

            try {
               if (!this.field_217849_F.containsKey(resourcelocation)) {
                  this.loadBlockstate(resourcelocation);
               }
            } catch (ModelBakery.BlockStateDefinitionException modelbakery$blockstatedefinitionexception) {
               LOGGER.warn(modelbakery$blockstatedefinitionexception.getMessage());
               this.field_217849_F.put(resourcelocation, iunbakedmodel);
            } catch (Exception exception) {
               LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", resourcelocation, modelLocation, exception);
               this.field_217849_F.put(resourcelocation, iunbakedmodel);
            } finally {
               this.field_217848_D.remove(resourcelocation);
            }
         }

         return this.field_217849_F.getOrDefault(modelLocation, iunbakedmodel);
      }
   }

   private void loadBlockstate(ResourceLocation blockstateLocation) throws Exception {
      if (!(blockstateLocation instanceof ModelResourceLocation)) {
         this.putModel(blockstateLocation, this.loadModel(blockstateLocation));
      } else {
         ModelResourceLocation modelresourcelocation = (ModelResourceLocation)blockstateLocation;
         if (Objects.equals(modelresourcelocation.getVariant(), "inventory")) {
            ResourceLocation resourcelocation2 = new ResourceLocation(blockstateLocation.getNamespace(), "item/" + blockstateLocation.getPath());
            BlockModel blockmodel = this.loadModel(resourcelocation2);
            this.putModel(modelresourcelocation, blockmodel);
            this.field_217849_F.put(resourcelocation2, blockmodel);
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(blockstateLocation.getNamespace(), blockstateLocation.getPath());
            StateContainer<Block, BlockState> statecontainer = Optional.ofNullable(STATE_CONTAINER_OVERRIDES.get(resourcelocation)).orElseGet(() -> {
               return Registry.BLOCK.getOrDefault(resourcelocation).getStateContainer();
            });
            this.containerHolder.setStateContainer(statecontainer);
            List<IProperty<?>> list = ImmutableList.copyOf(this.field_225365_D.func_225310_a(statecontainer.getOwner()));
            ImmutableList<BlockState> immutablelist = statecontainer.getValidStates();
            Map<ModelResourceLocation, BlockState> map = Maps.newHashMap();
            immutablelist.forEach((p_217830_2_) -> {
               BlockState blockstate = map.put(BlockModelShapes.getModelLocation(resourcelocation, p_217830_2_), p_217830_2_);
            });
            Map<BlockState, Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>>> map1 = Maps.newHashMap();
            ResourceLocation resourcelocation1 = new ResourceLocation(blockstateLocation.getNamespace(), "blockstates/" + blockstateLocation.getPath() + ".json");
            IUnbakedModel iunbakedmodel = this.field_217849_F.get(MODEL_MISSING);
            ModelBakery.ModelListWrapper modelbakery$modellistwrapper = new ModelBakery.ModelListWrapper(ImmutableList.of(iunbakedmodel), ImmutableList.of());
            Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>> pair = Pair.of(iunbakedmodel, () -> {
               return modelbakery$modellistwrapper;
            });

            {
               try {
                  {
                     List<Pair<String, BlockModelDefinition>> lvt_13_5_;
                     {
                        lvt_13_5_ = this.resourceManager.getAllResources(resourcelocation1).stream().map((p_217839_1_) -> {
                           try (InputStream inputstream = p_217839_1_.getInputStream()) {
                              Pair<String, BlockModelDefinition> pair2 = Pair.of(p_217839_1_.getPackName(), BlockModelDefinition.fromJson(this.containerHolder, new InputStreamReader(inputstream, StandardCharsets.UTF_8), blockstateLocation, this, this::putModel));
                              return pair2;
                           } catch (Exception exception1) {
                              throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", p_217839_1_.getLocation(), p_217839_1_.getPackName(), exception1.getMessage()));
                           }
                        }).collect(Collectors.toList());
                     }

                     for(Pair<String, BlockModelDefinition> pair1 : lvt_13_5_) {
                        BlockModelDefinition blockmodeldefinition = pair1.getSecond();
                        Map<BlockState, Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>>> map2 = Maps.newIdentityHashMap();
                        Multipart multipart;
                        if (blockmodeldefinition.hasMultipartData()) {
                           multipart = blockmodeldefinition.getMultipartData();
                           immutablelist.forEach((p_217832_3_) -> {
                              Pair pair2 = map2.put(p_217832_3_, Pair.of(multipart, () -> {
                                 return ModelBakery.ModelListWrapper.func_225335_a(p_217832_3_, multipart, list);
                              }));
                           });
                        } else {
                           multipart = null;
                        }

                        blockmodeldefinition.getVariants().forEach((p_225351_9_, p_225351_10_) -> {
                           try {
                              immutablelist.stream().filter(parseVariantKey(statecontainer, p_225351_9_)).forEach((p_225348_6_) -> {
                                 Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>> pair2 = map2.put(p_225348_6_, Pair.of(p_225351_10_, () -> {
                                    return ModelBakery.ModelListWrapper.func_225336_a(p_225348_6_, p_225351_10_, list);
                                 }));
                                 if (pair2 != null && pair2.getFirst() != multipart) {
                                    map2.put(p_225348_6_, pair);
                                    throw new RuntimeException("Overlapping definition with: " + (String)blockmodeldefinition.getVariants().entrySet().stream().filter((p_217831_1_) -> {
                                       return p_217831_1_.getValue() == pair2.getFirst();
                                    }).findFirst().get().getKey());
                                 }
                              });
                           } catch (Exception exception1) {
                              LOGGER.warn("Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}", resourcelocation1, pair1.getFirst(), p_225351_9_, exception1.getMessage());
                           }

                        });
                        map1.putAll(map2);
                     }
                  }
               } catch (IOException ioexception) {
                  LOGGER.warn("Exception loading blockstate definition: {}: {}", resourcelocation1, ioexception);
               } catch (ModelBakery.BlockStateDefinitionException modelbakery$blockstatedefinitionexception) {
                  throw modelbakery$blockstatedefinitionexception;
               } catch (Exception exception) {
                  throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s': %s", resourcelocation1, exception));
               } finally {
                  {
                     HashMap<ModelBakery.ModelListWrapper, Set<BlockState>> map3 = Maps.newHashMap();
                     map.forEach((p_225355_5_, p_225355_6_) -> {
                        Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>> pair2 = map1.get(p_225355_6_);
                        if (pair2 == null) {
                           LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", resourcelocation1, p_225355_5_);
                           pair2 = pair;
                        }

                        this.putModel(p_225355_5_, pair2.getFirst());

                        try {
                           ModelBakery.ModelListWrapper modelbakery$modellistwrapper1 = pair2.getSecond().get();
                           map3.computeIfAbsent(modelbakery$modellistwrapper1, (p_225363_0_) -> {
                              return Sets.newIdentityHashSet();
                           }).add(p_225355_6_);
                        } catch (Exception exception1) {
                           LOGGER.warn("Exception evaluating model definition: '{}'", p_225355_5_, exception1);
                        }

                     });
                     map3.forEach((p_225359_1_, p_225359_2_) -> {
                        Iterator<BlockState> iterator = p_225359_2_.iterator();

                        while(iterator.hasNext()) {
                           BlockState blockstate = iterator.next();
                           if (blockstate.getRenderType() != BlockRenderType.MODEL) {
                              iterator.remove();
                              this.field_225367_M.put(blockstate, 0);
                           }
                        }

                        if (p_225359_2_.size() > 1) {
                           this.func_225352_a(p_225359_2_);
                        }

                     });
                  }
               }
                  }
                  }

                     }
                  }

   private void putModel(ResourceLocation p_209593_1_, IUnbakedModel p_209593_2_) {
      this.field_217849_F.put(p_209593_1_, p_209593_2_);
      this.field_217848_D.addAll(p_209593_2_.getDependencies());
   }

   private void func_217843_a(ModelResourceLocation p_217843_1_) {
      IUnbakedModel iunbakedmodel = this.getUnbakedModel(p_217843_1_);
      this.field_217849_F.put(p_217843_1_, iunbakedmodel);
      this.field_217851_H.put(p_217843_1_, iunbakedmodel);
   }

   private void func_225352_a(Iterable<BlockState> p_225352_1_) {
      int i = this.field_225366_L++;
      p_225352_1_.forEach((p_225358_2_) -> {
         this.field_225367_M.put(p_225358_2_, i);
      });
   }

   @Nullable
   public IBakedModel func_217845_a(ResourceLocation p_217845_1_, ISprite p_217845_2_) {
      return getBakedModel(p_217845_1_, p_217845_2_, this.textureMap::getSprite, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM);
   }

   @Nullable
   public IBakedModel getBakedModel(ResourceLocation p_217845_1_, ISprite p_217845_2_, java.util.function.Function<ResourceLocation, net.minecraft.client.renderer.texture.TextureAtlasSprite> textureGetter, net.minecraft.client.renderer.vertex.VertexFormat format) {
      Triple<ResourceLocation, net.minecraftforge.common.model.IModelState, Boolean> triple = Triple.of(p_217845_1_, p_217845_2_.getState(), p_217845_2_.isUvLock());
      if (this.field_217850_G.containsKey(triple)) {
         return this.field_217850_G.get(triple);
      } else {
         IUnbakedModel iunbakedmodel = this.getUnbakedModel(p_217845_1_);
         if (iunbakedmodel instanceof BlockModel) {
            BlockModel blockmodel = (BlockModel)iunbakedmodel;
            if (blockmodel.getRootModel() == MODEL_GENERATED) {
               return field_217854_z.makeItemModel(textureGetter, blockmodel).func_217644_a(this, blockmodel, textureGetter, p_217845_2_);
            }
         }

         IBakedModel ibakedmodel = iunbakedmodel.bake(this, this.textureMap::getSprite, p_217845_2_, format);
         this.field_217850_G.put(triple, ibakedmodel);
         return ibakedmodel;
      }
   }

   protected BlockModel loadModel(ResourceLocation location) throws IOException {
      Reader reader = null;
      IResource iresource = null;

      BlockModel lvt_5_2_;
      try {
         String s = location.getPath();
         if (!"builtin/generated".equals(s)) {
            if ("builtin/entity".equals(s)) {
               lvt_5_2_ = MODEL_ENTITY;
               return lvt_5_2_;
            }

            if (s.startsWith("builtin/")) {
               String s2 = s.substring("builtin/".length());
               String s1 = BUILT_IN_MODELS.get(s2);
               if (s1 == null) {
                  throw new FileNotFoundException(location.toString());
               }

               reader = new StringReader(s1);
            } else {
               iresource = this.resourceManager.getResource(new ResourceLocation(location.getNamespace(), "models/" + location.getPath() + ".json"));
               reader = new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8);
            }

            lvt_5_2_ = BlockModel.deserialize(reader);
            lvt_5_2_.name = location.toString();
            BlockModel blockmodel1 = lvt_5_2_;
            return blockmodel1;
         }

         lvt_5_2_ = MODEL_GENERATED;
      } finally {
         IOUtils.closeQuietly(reader);
         IOUtils.closeQuietly((Closeable)iresource);
      }

      return lvt_5_2_;
   }

   public Map<ResourceLocation, IBakedModel> func_217846_a() {
      return this.field_217852_I;
   }

   public Object2IntMap<BlockState> func_225354_b() {
      return this.field_225367_M;
   }

   public Set<ResourceLocation> getSpecialModels() {
      return java.util.Collections.emptySet();
   }

   @OnlyIn(Dist.CLIENT)
   static class BlockStateDefinitionException extends RuntimeException {
      public BlockStateDefinitionException(String message) {
         super(message);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class ModelListWrapper {
      private final List<IUnbakedModel> field_225339_a;
      private final List<Object> field_225340_b;

      public ModelListWrapper(List<IUnbakedModel> p_i51613_1_, List<Object> p_i51613_2_) {
         this.field_225339_a = p_i51613_1_;
         this.field_225340_b = p_i51613_2_;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (!(p_equals_1_ instanceof ModelBakery.ModelListWrapper)) {
            return false;
         } else {
            ModelBakery.ModelListWrapper modelbakery$modellistwrapper = (ModelBakery.ModelListWrapper)p_equals_1_;
            return Objects.equals(this.field_225339_a, modelbakery$modellistwrapper.field_225339_a) && Objects.equals(this.field_225340_b, modelbakery$modellistwrapper.field_225340_b);
         }
      }

      public int hashCode() {
         return 31 * this.field_225339_a.hashCode() + this.field_225340_b.hashCode();
      }

      public static ModelBakery.ModelListWrapper func_225335_a(BlockState p_225335_0_, Multipart p_225335_1_, Collection<IProperty<?>> p_225335_2_) {
         StateContainer<Block, BlockState> statecontainer = p_225335_0_.getBlock().getStateContainer();
         List<IUnbakedModel> list = p_225335_1_.getSelectors().stream().filter((p_225338_2_) -> {
            return p_225338_2_.getPredicate(statecontainer).test(p_225335_0_);
         }).map(Selector::getVariantList).collect(ImmutableList.toImmutableList());
         List<Object> list1 = func_225337_a(p_225335_0_, p_225335_2_);
         return new ModelBakery.ModelListWrapper(list, list1);
      }

      public static ModelBakery.ModelListWrapper func_225336_a(BlockState p_225336_0_, IUnbakedModel p_225336_1_, Collection<IProperty<?>> p_225336_2_) {
         List<Object> list = func_225337_a(p_225336_0_, p_225336_2_);
         return new ModelBakery.ModelListWrapper(ImmutableList.of(p_225336_1_), list);
      }

      private static List<Object> func_225337_a(BlockState p_225337_0_, Collection<IProperty<?>> p_225337_1_) {
         return p_225337_1_.stream().<Object>map(p_225337_0_::get).collect(ImmutableList.toImmutableList());
      }
   }
}