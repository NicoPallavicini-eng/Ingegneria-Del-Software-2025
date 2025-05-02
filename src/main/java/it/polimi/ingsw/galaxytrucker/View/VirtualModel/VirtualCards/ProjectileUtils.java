package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Direction;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Ship;

import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;

public class ProjectileUtils {
    static void removeHitTileColumn(Direction direction, Ship ship, int diceRoll) {
        int columnSize = ship.getColumnListTiles(diceRoll).size();
        if (direction == NORTH) {
            if (columnSize == 7) {
                ship.removeTileOnFloorPlan(10, diceRoll);
            } else if (columnSize == 6) {
                if (ship.getColumnListTiles(10).isEmpty()) {
                    ship.removeTileOnFloorPlan(9, diceRoll);
                } else {
                    ship.removeTileOnFloorPlan(10, diceRoll);
                }
            } else if (columnSize == 5) {
                if (ship.getColumnListTiles(10).isEmpty()) {
                    if (ship.getRowListTiles(9).isEmpty()) {
                        ship.removeTileOnFloorPlan(8, diceRoll);
                    } else {
                        ship.removeTileOnFloorPlan(9, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(10, diceRoll);
                }
            } else if (columnSize == 4) {
                if (ship.getColumnListTiles(10).isEmpty()) {
                    if (ship.getRowListTiles(9).isEmpty()) {
                        if (ship.getColumnListTiles(8).isEmpty()) {
                            ship.removeTileOnFloorPlan(7, diceRoll);
                        } else {
                            ship.removeTileOnFloorPlan(8, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(9, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(10, diceRoll);
                }
            } else if (columnSize == 3) {
                if (ship.getColumnListTiles(10).isEmpty()) {
                    if (ship.getRowListTiles(9).isEmpty()) {
                        if (ship.getColumnListTiles(8).isEmpty()) {
                            if (ship.getRowListTiles(7).isEmpty()) {
                                ship.removeTileOnFloorPlan(6, diceRoll);
                            } else {
                                ship.removeTileOnFloorPlan(7, diceRoll);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(8, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(9, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(10, diceRoll);
                }
            } else if (columnSize == 2) {
                if (ship.getColumnListTiles(10).isEmpty()) {
                    if (ship.getRowListTiles(9).isEmpty()) {
                        if (ship.getColumnListTiles(8).isEmpty()) {
                            if (ship.getRowListTiles(7).isEmpty()) {
                                if (ship.getColumnListTiles(6).isEmpty()) {
                                    ship.removeTileOnFloorPlan(5, diceRoll);
                                } else {
                                    ship.removeTileOnFloorPlan(6, diceRoll);
                                }
                            } else {
                                ship.removeTileOnFloorPlan(7, diceRoll);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(8, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(9, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(10, diceRoll);
                }
            } else if (columnSize == 1) {
                if (ship.getColumnListTiles(10).isEmpty()) {
                    if (ship.getRowListTiles(9).isEmpty()) {
                        if (ship.getColumnListTiles(8).isEmpty()) {
                            if (ship.getRowListTiles(7).isEmpty()) {
                                if (ship.getColumnListTiles(6).isEmpty()) {
                                    if (ship.getRowListTiles(5).isEmpty()) {
                                        ship.removeTileOnFloorPlan(4, diceRoll);
                                    } else {
                                        ship.removeTileOnFloorPlan(5, diceRoll);
                                    }
                                } else {
                                    ship.removeTileOnFloorPlan(6, diceRoll);
                                }
                            } else {
                                ship.removeTileOnFloorPlan(7, diceRoll);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(8, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(9, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(10, diceRoll);
                }
            }
        } else if (direction == SOUTH) {
            if (columnSize == 7) {
                ship.removeTileOnFloorPlan(4, diceRoll);
            } else if (columnSize == 6) {
                if (ship.getColumnListTiles(4).isEmpty()) {
                    ship.removeTileOnFloorPlan(5, diceRoll);
                } else {
                    ship.removeTileOnFloorPlan(4, diceRoll);
                }
            } else if (columnSize == 5) {
                if (ship.getColumnListTiles(4).isEmpty()) {
                    if (ship.getRowListTiles(5).isEmpty()) {
                        ship.removeTileOnFloorPlan(6, diceRoll);
                    } else {
                        ship.removeTileOnFloorPlan(5, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(4, diceRoll);
                }
            } else if (columnSize == 4) {
                if (ship.getColumnListTiles(4).isEmpty()) {
                    if (ship.getRowListTiles(5).isEmpty()) {
                        if (ship.getColumnListTiles(6).isEmpty()) {
                            ship.removeTileOnFloorPlan(7, diceRoll);
                        } else {
                            ship.removeTileOnFloorPlan(6, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(5, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(4, diceRoll);
                }
            } else if (columnSize == 3) {
                if (ship.getColumnListTiles(4).isEmpty()) {
                    if (ship.getRowListTiles(5).isEmpty()) {
                        if (ship.getColumnListTiles(6).isEmpty()) {
                            if (ship.getRowListTiles(7).isEmpty()) {
                                ship.removeTileOnFloorPlan(8, diceRoll);
                            } else {
                                ship.removeTileOnFloorPlan(7, diceRoll);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(6, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(5, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(4, diceRoll);
                }
            } else if (columnSize == 2) {
                if (ship.getColumnListTiles(4).isEmpty()) {
                    if (ship.getRowListTiles(5).isEmpty()) {
                        if (ship.getColumnListTiles(6).isEmpty()) {
                            if (ship.getRowListTiles(7).isEmpty()) {
                                if (ship.getColumnListTiles(8).isEmpty()) {
                                    ship.removeTileOnFloorPlan(9, diceRoll);
                                } else {
                                    ship.removeTileOnFloorPlan(8, diceRoll);
                                }
                            } else {
                                ship.removeTileOnFloorPlan(7, diceRoll);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(6, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(5, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(4, diceRoll);
                }
            } else if (columnSize == 1) {
                if (ship.getColumnListTiles(4).isEmpty()) {
                    if (ship.getRowListTiles(5).isEmpty()) {
                        if (ship.getColumnListTiles(6).isEmpty()) {
                            if (ship.getRowListTiles(7).isEmpty()) {
                                if (ship.getColumnListTiles(8).isEmpty()) {
                                    if (ship.getRowListTiles(9).isEmpty()) {
                                        ship.removeTileOnFloorPlan(10, diceRoll);
                                    } else {
                                        ship.removeTileOnFloorPlan(9, diceRoll);
                                    }
                                } else {
                                    ship.removeTileOnFloorPlan(8, diceRoll);
                                }
                            } else {
                                ship.removeTileOnFloorPlan(7, diceRoll);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(6, diceRoll);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(5, diceRoll);
                    }
                } else {
                    ship.removeTileOnFloorPlan(4, diceRoll);
                }
            }
        }
    }

    static void removeHitTileRow(Direction direction, Ship ship, int diceRoll) {
        int rowSize = ship.getRowListTiles(diceRoll).size();
        if (direction == WEST) {
            if (rowSize == 5) {
                ship.removeTileOnFloorPlan(diceRoll, 9);
            } else if (rowSize == 4) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    ship.removeTileOnFloorPlan(diceRoll, 8);
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            } else if (rowSize == 3) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    if (ship.getRowListTiles(8).isEmpty()) {
                        ship.removeTileOnFloorPlan(diceRoll, 7);
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 8);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            } else if (rowSize == 2) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    if (ship.getRowListTiles(8).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            ship.removeTileOnFloorPlan(diceRoll, 6);
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 8);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            } else if (rowSize == 1) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    if (ship.getRowListTiles(8).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            if (ship.getRowListTiles(6).isEmpty()) {
                                ship.removeTileOnFloorPlan(diceRoll, 5);
                            } else {
                                ship.removeTileOnFloorPlan(diceRoll, 6);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 8);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            }
        } else if (direction == EAST) {
            if (rowSize == 5) {
                ship.removeTileOnFloorPlan(diceRoll, 5);
            } else if (rowSize == 4) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    ship.removeTileOnFloorPlan(diceRoll, 4);
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            } else if (rowSize == 3) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    if (ship.getRowListTiles(6).isEmpty()) {
                        ship.removeTileOnFloorPlan(diceRoll, 7);
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 6);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            } else if (rowSize == 2) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    if (ship.getRowListTiles(6).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            ship.removeTileOnFloorPlan(diceRoll, 8);
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 6);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            } else if (rowSize == 1) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    if (ship.getRowListTiles(6).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            if (ship.getRowListTiles(8).isEmpty()) {
                                ship.removeTileOnFloorPlan(diceRoll, 9);
                            } else {
                                ship.removeTileOnFloorPlan(diceRoll, 8);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 6);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            }
        }
    }
}
