library(ggplot2)
library(tidyverse)
library(modelr)
library(plyr)
library(stringr)
library(reshape2)

# Open csv
fragments <- read.csv2("fragmentsIMAMCRS.csv", dec=".", colClasses=c("integer","integer","character","integer","character","character","numeric","numeric","numeric","numeric","numeric","numeric", "numeric"), na.strings=c("."))
survey_scores <- read.csv2("survey_scores2.csv", dec=".", colClasses=c("integer","numeric"))

# Add survey scores to fragments
for (i in 1:nrow(fragments_init)) {
  fragments[i,]$Survey_Score <- survey_scores$Score[which(survey_scores$ID_Email == fragments[i,]$ID_Email)]
}

# Create two sets of data
ids_test <- c(1,6,11,14,15,16,39,50,51,53,61,63,69,73,78,82,89,92,93,98)
fragments_train <- fragments[which(!is.element(fragments$ID_Email, ids_test)),]
fragments_test <- fragments[which(is.element(fragments$ID_Email, ids_test)),]

cubic <- function(c,x) {
  a <- 2*(c-1)
  b <- 3*(1-c)
  a*x^3 + b*x^2 + c*x
}

sigmoid <- function(x) {
  1/(1+exp(-x))
}

modelSize <- function(a, data) {
  total <- 0
  nb_frags <- nrow(data)
  
  for (i in 1:nb_frags) {
    #score <- ((1/(1+data[i,]$Size*0.05)) ^ a[1]) + (data[i,]$Indico ^ a[2]) + (data[i,]$Microsoft^ a[3]) + (data[i,]$Aylien^ a[4]) + (data[i,]$Meaning_Cloud^ a[5]) + (data[i,]$Repustate^ a[6])
    score <- (data[i,]$Size * a[1]) + (data[i,]$Indico * a[2]) + (data[i,]$Microsoft * a[3]) + (data[i,]$Aylien * a[4]) + (data[i,]$Meaning_Cloud * a[5]) + (data[i,]$Repustate * a[6])
    
    if (!is.na(data[i,]$Smileys)) {
      score <- score * (data[i,]$Smileys * a[7])
    }
    
    if (data[i,]$Type=="BOLD") {
      score <- cubic(tanh(a[8]),score)
    } else if (data[i,]$Type=="CAPS") {
      score <- cubic(tanh(a[9]),score)
    }
    
    total <- total + score
  }
  
  total/(nb_frags)
}

measure_distance <- function(mod, data) {
  diff <- rnorm(80)
  j <- 1
  
  for (i in 1:100) {
    if (! i %in% ids_test) {
      diff[j] <- data[which(data$ID_Email == i),]$Survey_Score[1] - modelSize(mod, data[which(data$ID_Email==i),])
      j <- j+1
    }
  }
  
  sqrt(mean(diff ^2))
}

plot_histo <- function(v) {
  ggplot(data = v) + 
    geom_histogram(aes(x=v), binwidth=0.06, fill=I("cornflowerblue"), col=I("black")) +
    geom_histogram(aes(x=v), fill=I("gainsboro"), alpha=0.9, color=I("black"), binwidth=0.025) +
    scale_x_continuous("Error", breaks=c(-0.35, -0.3,-0.25,-0.20,-0.15,-0.1,-0.05,0,0.05,0.1,0.15,0.2,0.25,0.3, 0.35)) +
    scale_y_continuous("Number of emails", breaks=c(seq(0,50,by=1)))
}

plot_histo(data.frame(vec=diff_test$x))

plot_histo_api <- function(data, title) {
  v <- data.frame(vec=abs(data)*100)
  m <- round(mean(abs(data)), digits=4)*100
  ggplot(data = v) + 
    ggtitle(title) +
    theme(plot.title = element_text(hjust = 0.5)) +
    geom_histogram(aes(x=v), binwidth=5, fill=I("cornflowerblue"), col=I("black")) +
    scale_x_continuous("Error", breaks=c(-2.5,0,2.5,5,7.5,10,12.5,15,17.5,20,22.5,25,27.5,30,32.5,35,37.5,40,42.5,45,47.5,50,52.5,55,57.5)) +
    scale_y_continuous("Number of emails", breaks=c(seq(0,50,by=1))) +
    geom_vline(xintercept=m, color="red") +
    annotate(geom="label", x=m, y=0, label=m, colour="red", size=3, angle=0)
}

print_train_score <- function(id) {
  print('Score :')
  print(fragments_train[which(fragments_train$ID_Email==id),]$Score[1])
  print('Survey :')
  print(fragments_train[which(fragments_train$ID_Email==id),]$Survey_Score[1])
}

print_test_score <- function(id) {
  print('Score :')
  print(fragments_test[which(fragments_test$ID_Email==id),]$Score[1])
  print('Survey :')
  print(fragments_test[which(fragments_test$ID_Email==id),]$Survey_Score[1])
}

# OPTIMIZATION
best <- optim(c(0,0,0,0,0,0,0,0,0), measure_distance, data = fragments_train)

best$par[8] <- tanh(best$par[8])
best$par[9] <- tanh(best$par[9])

# Scoring fragments
for (i in 1:100) {
  if (i %in% ids_test) {
    fragments_test[which(fragments_test$ID_Email == i),]$Score <- modelSize(best$par, fragments_test[which(fragments_test$ID_Email == i),])
  } else {
    fragments_train[which(fragments_train$ID_Email == i),]$Score <- modelSize(best$par, fragments_train[which(fragments_train$ID_Email == i),])
  }
  
  fragments[which(fragments$ID_Email == i),]$Score <- modelSize(best$par, fragments[which(fragments$ID_Email == i),])
}

# Calculate errors train set
diff_train <- aggregate(fragments_train$Survey_Score - fragments_train$Score, by=list(fragments_train$ID_Email), mean)
mean(abs(diff_train$x))
sd(abs(diff_train$x))

ggplot(data = diff_train) + 
  geom_bar(aes(x=Group.1,y=x), stat="identity")

plot_histo(data.frame(vec=diff_train$x))

# Calculate errors test set
diff_test <- aggregate(fragments_test$Survey_Score - fragments_test$Score, by=list(fragments_test$ID_Email), mean)
mean(abs(diff_test$x))
sd(sqrt(diff_test$x^2))

ggplot(data = diff_test) + 
  geom_bar(aes(x=Group.1,y=x), stat="identity")

plot_histo(data.frame(vec=diff_test$x))

