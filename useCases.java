
/**
 * Write a description of class useCases here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
/*
public class useCases
{
    public static void method() {
        
        } else { // both are not null
            if (FC == inTheWay && PC == inTheWay) {
                if(FC a == 0) {
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in 
                    }
                }
                else if (FC a > 0) {
                    if(PC a == 0) {
                        not letting in 
                    }
                    else if (PC > 0) {
                        not letting in 
                    } else { // PC a < 0
                        not letting in 
                    }
                } else { // FC a < 0
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in 
                    }
                }
            }
            else if (FC == inTheWay && PC != inTheWay) {
                if(FC a == 0) {
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in 
                    }
                }
                else if (FC a > 0) {
                    if(PC a == 0) {
                        not letting in 
                    }
                    else if (PC > 0) {
                        not letting in 
                    } else { // PC a < 0
                        not letting in 
                    }
                } else { // FC a < 0
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in
                    }
                }
            }
            else if (FC != inTheWay && PC == inTheWay) {
                if(FC a == 0) {
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in 
                    }
                }
                else if (FC a > 0) {
                    if(PC a == 0) {
                        not letting in 
                    }
                    else if (PC > 0) {
                        not letting in 
                    } else { // PC a < 0
                        not letting in 
                    }
                } else { // FC a < 0
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in 
                    }
                }
            }
            else  { //neither are in the way
                if(FC a == 0) {
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        not letting in 
                    }
                }
                else if (FC a > 0) {
                    if(PC a == 0) {
                        not letting in 
                    }
                    else if (PC > 0) {
                        not letting in 
                    } else { // PC a < 0
                        not letting in 
                    }
                } else { // FC a < 0
                    if(PC a == 0) {
                        end;
                    }
                    else if (PC > 0) {
                        end;
                    } else { // PC a < 0
                        crash
                    }
                }
            }
        }
    }
} */

//one car in way use case (ungeneralized)
/*} else if (carInWay(precedingCar).length != 0 || carInWay(followingCar).length == 0) {
                double gap = Math.abs(followingCar.distance) - (0.5 * length + 0.5 * followingCar.length);
                double overlap = 0.5*(precedingCar.length + this.length) - Math.abs(precedingCar.distance);
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration == 0) {
                        if(gap >= overlap) {
                            return("Time: 0" + "\nAcceleration: " + accelerationNeeded(precedingCar));
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    } else { //followingCar.acceleration >= 0 ????
                        double displacement = (followingCar.velocity * 3) + (0.5 * followingCar.acceleration * 3);
                        if(gap - displacement >= overlap) {
                            return("Time: 0" + "\nAcceleration: " + accelerationNeeded(precedingCar));
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration <= 0) { //?????
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                    } else { //followingCar.acceleration > 0 ?????
                        double displacement = (followingCar.velocity * timeTillSafe(precedingCar)) + 
                                                (0.5 * followingCar.acceleration * timeTillSafe(precedingCar));
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            if(gap - displacement >= 0) {
                                return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration.");
                            }
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    }
                } else { //precedingCar.acceleration < 0
                    double displacement = (followingCar.velocity * timeTillSafe(precedingCar)) + 
                                                (0.5 * followingCar.acceleration * timeTillSafe(precedingCar));
                    if(followingCar.acceleration == 0) {
                        if(gap >= precedingCar.length) {
                            return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    } else if (followingCar.acceleration > 0) {
                        if(gap - displacement >= precedingCar.length) {
                            return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    } else { //followingCar.acceleration < 0 ?????
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            if(gap - displacement >= precedingCar.length) {
                                return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration.");
                            }
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    }
                }
            } else if (carInWay(precedingCar).length == 0 && carInWay(followingCar).length != 0) {
                double gap = Math.abs(precedingCar.distance) - (0.5 * length + 0.5 * precedingCar.length);
                double overlap = 0.5 * (followingCar.length + this.length) - Math.abs(followingCar.distance);
                if (followingCar.acceleration == 0) {
                    if (precedingCar.acceleration == 0) {
                        if(gap >= overlap) {
                            return("Time: 0" + "\nAcceleration: " + accelerationNeeded(followingCar)); 
                        } else {
                            return("The other cars need to change acceleration");
                        }
                    } else { //precedingCar.acceleration >= 0 ???
                        double displacement = (precedingCar.velocity * 3) + (0.5 * precedingCar.acceleration * 3);
                        if(gap + displacement >= overlap) {
                            return("Time: 0" + "\nAcceleration: " + accelerationNeeded(followingCar)); 
                        } else {
                            return("The other cars need to change acceleration");
                        }
                    }
                } else if (followingCar.acceleration > 0) {
                    double displacement = (precedingCar.velocity * timeTillSafe(followingCar)) + 
                                            (0.5 * precedingCar.acceleration * timeTillSafe(followingCar));
                    if (precedingCar.acceleration == 0) {
                        if(gap >= followingCar.length) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return("The other cars need to change acceleration");
                        }
                    } else if (precedingCar.acceleration > 0) {
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            if(gap + displacement >= followingCar.length) {
                                return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration.");
                            }
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    } else { //precedingCar.acceleration < 0 ???
                        if(gap + displacement >= followingCar.length) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    }
                } else { //followingCar.acceleration < 0
                    double displacement = (precedingCar.velocity * timeTillSafe(followingCar)) + 
                                            (0.5 * precedingCar.acceleration * timeTillSafe(followingCar));
                    if (precedingCar.acceleration >= 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                    } else {
                        if(gap + displacement >= 0) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration.");
                        }
                    }
                } */
// both cars in the way use case (not generalized)
/*if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: " + 
                                accelerationNeeded(precedingCar));
                        //need to wait for FC to slow down THEN decelerate
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        double timeFC = timeTillSafe(followingCar);
                        double timePC = timeTillSafe(precedingCar);
                        if (timePC > timeFC) {
                            return("Time: " + timePC + "\nAcceleration: 0");
                        } else {
                            return("Time: " + timeFC + "\nAcceleration: 0");
                        } // return larger time
                        //need to wait for both to be out of way, a = 0;
                    } else if (followingCar.acceleration == 0) {
                        return("Time: " + timeTillSafe(precedingCar) + 
                                "\nAcceleration: " + accelerationNeeded(followingCar));
                        //need to wait for PC to speed up THEN accelerate
                    } else { //followingCar.acceleration > 0
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    }
                } else { //precedingCar.acceleration < 0
                    if(followingCar.acceleration < 0) {
                        if (Math.abs(precedingCar.acceleration) <= Math.abs(followingCar.acceleration)) {
                            return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                } */
